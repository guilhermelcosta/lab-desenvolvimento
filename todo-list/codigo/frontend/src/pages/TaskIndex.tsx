import React, {useEffect, useState} from 'react';
import {getAllTasks} from '../services/TaskService';
import {Task} from "../interfaces/Task";
import {MessageConstants} from "../utils/MessageConstants";
import LoadingSpinner from "../components/LoadingSpinner";


const TaskIndex: React.FC = () => {

    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect((): void => {
        const fetchTasks = async (): Promise<void> => {
            try {
                const tasksFound: Task[] = await getAllTasks();
                setTasks(tasksFound);
                // setLoading(false);
            } catch (err) {
                setError(MessageConstants.ERROR_MESSAGES.failedToFetchTasks);
                setLoading(false);
            }
        };
        fetchTasks();
    }, []);

    if (loading) {
        return <LoadingSpinner/>;
    }

    if (error) {
        return <p>{error}</p>;
    }

    return (
        <div>
            <h1>Página inicial</h1>
            <ul>
                {tasks.map(task => (
                    <li key={task.id}>
                        <h2>{task.title}</h2>
                        <p>{task.description}</p>
                        <p>{task.status}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TaskIndex;
