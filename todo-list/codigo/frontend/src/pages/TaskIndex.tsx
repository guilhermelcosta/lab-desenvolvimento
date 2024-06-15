import React, {useEffect, useState} from 'react';
import {getAllTasks} from '../services/TaskService';
import {Task} from "../interfaces/Task";
import {MessageConstants} from "../utils/MessageConstants";
import LoadingSpinner from "../components/loading-spinner/LoadingSpinner";
import Snackbar from '@mui/material/Snackbar';
import styles from './TaskIndex.module.css';
import {Checkbox} from "@mui/material";
import ReactPaginate from 'react-paginate';
import {NumberConstants} from "../utils/NumberConstants";

const TaskIndex: React.FC = () => {
    const [open, setOpen] = React.useState(false);
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<boolean | null>(null);
    const [currentPage, setCurrentPage] = useState(NumberConstants.ZERO);
    const tasksPerPage: number = NumberConstants.FIVE;
    const offset: number = currentPage * tasksPerPage;
    const currentTasks: Task[] = tasks.slice(offset, offset + tasksPerPage);
    const pageCount: number = Math.ceil(tasks.length / tasksPerPage);

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

    const handlePageClick = (data: { selected: number }): void => {
        setCurrentPage(data.selected);
    };

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
                {currentTasks.map(task => (
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
                        {task.tag && (
                            <p className={styles.taskInfo}>
                                <span>{task.tag}</span>
                            </p>
                        )}
                    </li>
                ))}
            </ul>
            <ReactPaginate
                previousLabel={'<'}
                nextLabel={'>'}
                breakLabel={'...'}
                pageCount={pageCount}
                marginPagesDisplayed={2}
                pageRangeDisplayed={5}
                onPageChange={handlePageClick}
                containerClassName={styles.pagination}
                activeClassName={styles.active}
            />
        </div>
    );
};

export default TaskIndex;
