export default function ListaTarefas() {

    const teste = async () => {
        console.log('Fazendo requisição');
        const resposta = await fetch('http://localhost:8080/todo-list/task')
        resposta.json().then(resposta => {
            console.log(resposta)
        })
    }
    teste();

    return (
        <div>Teste
            <p>teste 2</p>
        </div>
    );
}
