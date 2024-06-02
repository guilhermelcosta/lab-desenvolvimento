import React from 'react';
import './App.css';
import {HashRouter as Router, Route, Routes} from 'react-router-dom'
import TaskIndex from "./pages/TaskIndex";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<TaskIndex/>}/>
            </Routes>
        </Router>
    );
}

export default App;
