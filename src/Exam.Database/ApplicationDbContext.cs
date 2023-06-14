using Microsoft.EntityFrameworkCore;

namespace Exam.Database;

public class ApplicationDbContext: DbContext
{
    public DbSet<DatabaseItem> Items => Set<DatabaseItem>();

    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options): base(options)
    { }
}