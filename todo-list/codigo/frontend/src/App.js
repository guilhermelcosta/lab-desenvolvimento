import './App.css';
import ListaTarefas from "./components/ListaTarefas";
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
