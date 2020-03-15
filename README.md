# github-api

## Heroku

## Docker

Two docker images were generated, in which one represents the API and the other the database used by it. These images are published on the Docker Hub, and to access them just access the following links. The images must be pulled in the order listed below.

- [github-api_github-db-mysql](https://hub.docker.com/r/jordankl/github-api_github-db-mysql)
- [github-api_github-api-java](https://hub.docker.com/r/jordankl/github-api_github-api-java)
</ul>

This will run API on port 8000 on your machine, so you can open it by navigating to http://localhost in your browser

## Commands
- API documentation (Swagger): http://localhost:8000/swagger-ui.html
- API documentation (Json): http://localhost:8000/v2/api-docs
