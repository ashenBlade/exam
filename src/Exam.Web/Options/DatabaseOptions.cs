using System.ComponentModel.DataAnnotations;

namespace Exam.Web.Options;

public class DatabaseOptions
{
    public const string SectionName = "Database";

    [Required]
    public string ConnectionString { get; set; } = null!;
}