import './App.css';
import ListaTarefas from "./componentes/ListaTarefas";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<ListaTarefas/>}/>
            </Routes>
        </Router>
    )
}

export default App;
