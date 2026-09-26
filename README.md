# 📋 TaskManager (Gerenciador de Tarefas)

Sistema de gerenciamento de tarefas desenvolvido em **Java** com foco na aplicação de boas práticas de programação, princípios de Orientação a Objetos (POO) e arquitetura limpa.

## 🚀 Funcionalidades
- **Cadastro de Tarefas:** Criação de tarefas normais e tarefas com nível de prioridade.
- **Gerenciamento de Status:** Listagem, conclusão e remoção de tarefas pelo ID.
- **Validação Robusta:** Tratamento de exceções personalizadas para dados inválidos (ex: títulos vazios).
- **Polimorfismo e Enums:** Uso de herança (`Tarefa` e `TarefaPrioritaria`) e tipos enumerados (`Prioridade`) para garantir maior segurança e legibilidade do código.

## 🛠️ Tecnologias Utilizadas
- **Java** (JDK 21+)
- **IDE NetBeans**
- **Git e GitHub** (Controle de Versão)

## 📁 Estrutura do Projeto
O projeto está organizado em pacotes seguindo o padrão de separação de responsabilidades:
- `app`: Contém a classe principal (`Main`) e a interface de interação com o usuário via console.
- `controller`: Contém a lógica de controle e gerenciamento das tarefas (`TaskManager`).
- `model`: Contém as classes de domínio (`Tarefa`, `TarefaPrioritaria` e o `enum` `Prioridade`).
- `exceptions`: Contém as exceções personalizadas do sistema (`TarefaException`).

## 💻 Como Executar
1. Clone este repositório na sua máquina:
   ```bash
   git clone [https://github.com/laralcf-netizen/Taskmanager.git](https://github.com/laralcf-netizen/Taskmanager.git)
