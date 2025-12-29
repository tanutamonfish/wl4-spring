docker run --name ps -e POSTGRES_USER=$DB_USER -e POSTGRES_PASSWORD=$DB_PASSWORD -e POSTGRES_DB=studs -p 5432:5432 -d postgres:latest
