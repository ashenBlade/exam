using Exam.Database;
using Exam.Web.Grpc;
using Exam.Web.Options;
using Microsoft.AspNetCore.Server.Kestrel.Core;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;

var builder = WebApplication.CreateBuilder(args);

builder.Services
       .AddOptions<DatabaseOptions>()
       .Bind(builder.Configuration.GetRequiredSection(DatabaseOptions.SectionName))
       .ValidateDataAnnotations()
       .ValidateOnStart();

builder.Services.AddGrpc();

builder.WebHost.ConfigureKestrel(kestrel =>
{
    kestrel.ListenAnyIP(8080, opt =>
    {
        opt.Protocols = HttpProtocols.Http2;
    });
    kestrel.ListenAnyIP(8081, opt =>
    {
        opt.Protocols = HttpProtocols.Http2;
    });
});


builder.Services.AddDbContext<ApplicationDbContext>((x, context) =>
{
    var options = x.GetRequiredService<IOptions<DatabaseOptions>>().Value;
    context.UseNpgsql(options.ConnectionString);
});

var app = builder.Build();

await using (var scope = app.Services.CreateAsyncScope())
{
    var context = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();
    await context.Database.EnsureCreatedAsync();
    if (!await context.Items.AnyAsync())
    {
        var seeds = Enumerable.Range(1, 10).Select(x => new DatabaseItem() {Value = Random.Shared.Next().ToString()});
        await context.Items.AddRangeAsync(seeds);
        await context.SaveChangesAsync();
    }
}

app.UseGrpcWeb();
app.MapGrpcService<ItemGrpcService>()
   .EnableGrpcWeb();

app.MapGet("/", () => "Hello World!");

app.Run();