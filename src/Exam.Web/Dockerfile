FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["Exam.Web/Exam.Web.csproj", "Exam.Web/"]
COPY ["Exam.Database/Exam.Database.csproj", "Exam.Database/"]
RUN dotnet restore "Exam.Web/Exam.Web.csproj"
COPY . .
WORKDIR "/src/Exam.Web"
RUN dotnet build "Exam.Web.csproj" -c Release -o /app/build
RUN dotnet dev-certs https --trust

FROM build AS publish
RUN dotnet publish "Exam.Web.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
COPY --from=build /root/.dotnet/corefx/cryptography/x509stores/my/* /root/.dotnet/corefx/cryptography/x509stores/my/
ENTRYPOINT ["dotnet", "Exam.Web.dll"]
