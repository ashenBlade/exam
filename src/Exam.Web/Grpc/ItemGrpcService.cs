using Exam.Database;
using Grpc.Core;
using Microsoft.EntityFrameworkCore;

namespace Exam.Web.Grpc;

public class ItemGrpcService: ItemService.ItemServiceBase
{
    private readonly ApplicationDbContext _context;
    private readonly ILogger<ItemGrpcService> _logger;

    public ItemGrpcService(ApplicationDbContext context, ILogger<ItemGrpcService> logger)
    {
        _context = context;
        _logger = logger;
    }

    public override async Task<CreateItemResponse> CreateItem(CreateItemRequest request, ServerCallContext context)
    {
        _logger.LogInformation("Запрос на создение нового item со значением {Value}", request.Value);
        var item = new Database.DatabaseItem() {Value = request.Value};
        var result = await _context.Items.AddAsync(item, context.CancellationToken);
        var e = result.Entity;
        _logger.LogInformation("Item со значением {Value} создан. Присвоен ID: {Id}", e.Value, e.Id);
        return new CreateItemResponse()
        {
            Item = new Item()
            {
                Id = e.Id,
                Value = e.Value
            }
        };
    }

    public override async Task<EditItemResponse> EditItem(EditItemRequest request, ServerCallContext context)
    {
        _logger.LogInformation("Запрос на обновление item по id: {Id}", request.Id);
        var oldItem = await _context.Items.FirstOrDefaultAsync(x => x.Id == request.Id);
        if (oldItem is null)
        {
            throw new InvalidOperationException($"Item с ID: {request.Id} не существует");
        }

        var updated = new Database.DatabaseItem() {Id = request.Id, Value = request.Value};
        _context.Items.Update(updated);
        await _context.SaveChangesAsync();
        _logger.LogInformation("Данные обновлены");
        return new EditItemResponse()
        {
            Item = new ()
            {
                Id = updated.Id,
                Value = updated.Value
            }
        };
    }

    public override async Task<RemoveItemResponse> RemoveItem(RemoveItemRequest request, ServerCallContext context)
    {
        var found = await _context.Items.FirstOrDefaultAsync(x => x.Id == request.Id);
        if (found is null)
        {
            throw new Exception($"Item с ID {request.Id} не существует");
        }

        _context.Items.Remove(found);
        await _context.SaveChangesAsync();
        return new RemoveItemResponse() {Deleted = new() {Id = found.Id, Value = found.Value}};
    }

    public override async Task<GetAllItemsResponse> GetAllItems(GetAllItemsRequest request, ServerCallContext context)
    {
        _logger.LogInformation("Запрос на получение всех Item");
        var items = await _context.Items.ToListAsync();
        return new GetAllItemsResponse() {Items = {items.Select(x => new Item() {Id = x.Id, Value = x.Value})}};
    }
}