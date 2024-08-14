# GitHub API Consumer

## Description

This Spring Boot application provides an API to list all GitHub repositories for a given user, excluding forks. For each repository, it lists the branches along with the latest commit SHA.

## Endpoints

### `GET /api/{username}`

Retrieves all non-fork repositories for the given username, including:
- Repository Name
- Owner Login
- Branches (name and last commit SHA)

### Error Handling

- **404 Not Found**: If the username does not exist, the response will be:
  ```json
  {
    "status": 404,
    "message": "User {username} not found"
  }
