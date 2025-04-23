terraform {
  required_providers {
    null = {
      source = "hashicorp/null"
      version = "~> 3.1"
    }
  }
}

provider "null" {}

resource "null_resource" "run_ansible" {
  provisioner "local-exec" {
    command = "ansible-playbook -i hosts.ini provision.yml"
  }
}
