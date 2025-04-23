
# DevOps Midterm Project

Overview:

This is a Spring Boot-based Student Registration web application enhanced with a full DevOps pipeline.  
The project includes CI/CD, Infrastructure as Code (IaC) using Ansible and Terraform,  
Blue-Green deployment with rollback, and monitoring capabilities.

---

Features:

- REST API for managing students (`/students/add`, `/students/all`, `/students/{id}`)
- Swagger UI for interactive API testing
- Unit tests for controllers and endpoints
- GitHub Actions CI pipeline (build, test, lint)
- Ansible-based local provisioning (Java, Maven, Git, folder setup)
- Terraform triggers Ansible automatically (IaC integration)
- Blue-Green deployment with `deploy.sh` and `rollback.sh`
- Health check system with `curl` + logs to `health.log`
- `/status` endpoint shows the latest health check result
- Auto rollback if health check fails after deployment

---


How to Run:

1. Provision environment using Terraform + Ansible:
   ```bash
   terraform -chdir=infra apply
   ```

2. Deploy application:
   ```bash
   ./deploy.sh
   ```

3. Run health check manually:
   ```bash
   ./healthcheck.sh
   ```

4. Auto rollback on failure:
   ```bash
   ./auto-check-and-rollback.sh
   ```

5. Start the Spring Boot application:
   ```bash
   cd devops-midterm
   mvn spring-boot:run
   ```

Access status endpoint:
   Visit [http://localhost:8080/status](http://localhost:8080/status)

---

GitHub Workflow:

- Branches used: `main`, `dev`
- GitHub Actions pipeline:
    - Runs on push or PR to `main` or `dev`
    - Runs unit tests and Checkstyle

---