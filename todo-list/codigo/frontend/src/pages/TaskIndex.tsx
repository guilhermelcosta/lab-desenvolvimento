import React, {useEffect, useState} from 'react';
import {getAllTasks} from '../services/TaskService';
import {Task} from "../interfaces/Task";
import {MessageConstants} from "../utils/MessageConstants";
import LoadingSpinner from "../components/loading-spinner/LoadingSpinner";
import Snackbar from '@mui/material/Snackbar';
import styles from './TaskIndex.module.css'
import {Checkbox} from "@mui/material";


const TaskIndex: React.FC = () => {

    const [open, setOpen] = React.useState(false);
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<boolean | null>(null);

    useEffect((): void => {
        const fetchTasks = async (): Promise<void> => {
            try {
                const tasksFound: Task[] = await getAllTasks();
                setTasks(tasksFound);
                setLoading(false);
            } catch (err) {
                setError(true);
                setLoading(false);
            }
        };
        fetchTasks();
    }, []);

    if (loading) {
        return <LoadingSpinner/>;
    }

    if (error) {
        return (
            <Snackbar
                open={open}
                message={MessageConstants.ERROR_MESSAGES.failedToFetchTasks}
            />
        );
    }

    return (
        <div className={styles.taskContainer}>
            <ul>
                <button>+</button>
                {tasks.map(task => (
                    <li className={styles.taskCard} key={task.id}>
                        <Checkbox/>
                        <div className={styles.taskTitleAndDescription}>
                            <h2>{task.title}</h2>
                            <p>{task.description}</p>
                        </div>
                        <p className={styles.taskInfo}>
                            <span>{task.status}</span>
                        </p>
                        <p className={styles.taskInfo}>
                            <span>{task.priority}</span>
                        </p>
                        {
                            task.tag &&
                            <p className={styles.taskInfo}>
                                <span>{task.tag}</span>
                            </p>
                        }
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TaskIndex;
