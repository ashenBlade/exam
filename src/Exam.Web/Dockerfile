FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["Exam.Web/Exam.Web.csproj", "Exam.Web/"]
RUN dotnet restore "Exam.Web/Exam.Web.csproj"
COPY . .
WORKDIR "/src/Exam.Web"
RUN dotnet build "Exam.Web.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "Exam.Web.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Exam.Web.dll"]
