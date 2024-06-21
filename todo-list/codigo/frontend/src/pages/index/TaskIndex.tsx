import React, {useEffect, useState} from 'react';
import {createTask, deleteTask, getAllTasks, updateTask} from '../../services/TaskService';
import {Task} from "../../interfaces/Task";
import LoadingSpinner from "../../components/loading-spinner/LoadingSpinner";
import Snackbar from '@mui/material/Snackbar';
import styles from './TaskIndex.module.css';
import {Checkbox, IconButton, Modal, Box, TextField, Button, MenuItem, Select} from "@mui/material";
import ReactPaginate from 'react-paginate';
import {TextConstants} from "../../utils/constants/TextConstants";
import {NumberConstants} from "../../utils/constants/NumberConstants";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';

const TaskIndex: React.FC = () => {
    const [open, setOpen] = React.useState(false);
    const [modalOpen, setModalOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState<string>('');
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<boolean | null>(null);
    const [currentPage, setCurrentPage] = useState(NumberConstants.ZERO);
    const [newTask, setNewTask] = useState<any>({
        title: '',
        description: '',
        priority: 'NO_PRIORITY',
        tag: 'SAUDE',
        days_to_complete: null,
        due_date: '',
        status: '',
    });
    const [editingTaskId, setEditingTaskId] = useState<string | null>(null);
    const [dateOption, setDateOption] = useState<string>('days_to_complete');
    const tasksPerPage: number = NumberConstants.FIVE;
    const offset: number = currentPage * tasksPerPage;
    const currentTasks: Task[] = tasks.slice(offset, offset + tasksPerPage);
    const pageCount: number = Math.ceil(tasks.length / tasksPerPage);

    useEffect((): void => {
        fetchTasks();
    }, []);

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

    const handlePageClick = (data: { selected: number }): void => {
        setCurrentPage(data.selected);
    };

    const getPriorityClass = (priority: string): string => {
        return TextConstants.PRIORITY_NAMES[priority]?.STYLE || '';
    };

    const getPriorityText = (priority: string): string => {
        return TextConstants.PRIORITY_NAMES[priority]?.NAME || priority;
    };

    const handleDeleteTask = async (id: string): Promise<void> => {
        try {
            await deleteTask(id);
            setTasks(tasks.filter(task => task.id !== id));
            setSnackbarMessage('Tarefa deletada com sucesso');
            setOpen(true);
        } catch (error) {
            setSnackbarMessage('Falha ao deletar tarefa');
            setOpen(true);
        }
    };

    const handleCloseSnackbar = (): void => {
        setOpen(false);
    };

    const handleOpenModal = (task?: Task): void => {
        if (task) {
            setNewTask({
                title: task.title,
                description: task.description,
                priority: task.priority,
                tag: task.tag,
                days_to_complete: task.days_to_complete,
                due_date: task.due_date ? new Date(task.due_date).toISOString().substring(0, 16) : '',
                status: task.status,
            });
            setDateOption(task.days_to_complete ? 'days_to_complete' : 'due_date');
            setEditingTaskId(task.id);
        } else {
            setNewTask({
                title: '',
                description: '',
                priority: 'NO_PRIORITY',
                tag: 'SAUDE',
                days_to_complete: null,
                due_date: '',
                status: '',
            });
            setDateOption('days_to_complete');
            setEditingTaskId(null);
        }
        setModalOpen(true);
    };

    const handleCloseModal = (): void => {
        setModalOpen(false);
    };

    const handleCreateOrUpdateTask = async (): Promise<void> => {
        const taskToSend = {...newTask};
        if (dateOption === 'due_date') {
            taskToSend.days_to_complete = null;
        } else {
            taskToSend.due_date = '';
        }

        if (!taskToSend.priority) {
            taskToSend.priority = 'NO_PRIORITY';
        }

        try {
            if (editingTaskId) {
                await updateTask(editingTaskId, taskToSend);
                await fetchTasks();
                setSnackbarMessage('Tarefa atualizada com sucesso');
            } else {
                await createTask(taskToSend)
                await fetchTasks();
                setSnackbarMessage('Tarefa criada com sucesso');
            }
            setOpen(true);
            handleCloseModal();
        } catch (error) {
            setSnackbarMessage(editingTaskId ? 'Falha ao atualizar tarefa' : 'Falha ao criar tarefa');
            setOpen(true);
        }
    };

    if (loading) {
        return <LoadingSpinner/>;
    }

    if (error) {
        return (
            <Snackbar
                open={open}
                message={TextConstants.ERROR_MESSAGES.failedToFetchTasks}
            />
        );
    }

    return (
        <div className={styles.taskContainer}>
            <ul>
                <IconButton
                    onClick={() => handleOpenModal()}
                    className={styles.addButton}>
                    <FontAwesomeIcon icon={faPlus}/>
                </IconButton>

                {currentTasks.map(task => (
                    <li className={styles.taskCard} key={task.id} onClick={() => handleOpenModal(task)}>
                        <Checkbox className={styles.customCheckbox}/>
                        <div className={styles.taskTitleAndDescription}>
                            <h2>{task.title}</h2>
                            <p>{task.description}</p>
                        </div>
                        <p className={styles.taskInfo}>
                            <span>{task.status}</span>
                        </p>
                        <p className={styles.taskInfo}>
                            <span className={getPriorityClass(task.priority)}>
                                {getPriorityText(task.priority)}
                            </span>
                        </p>
                        {task.tag && (
                            <p className={styles.taskInfo}>
                                <span>{task.tag}</span>
                            </p>
                        )}
                        <p>
                            <IconButton onClick={(e) => {
                                e.stopPropagation();
                                handleDeleteTask(task.id);
                            }}>
                                <FontAwesomeIcon icon={faTrash}/>
                            </IconButton>
                        </p>
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

            <Snackbar
                open={open}
                autoHideDuration={6000}
                onClose={handleCloseSnackbar}
                message={snackbarMessage}
            />

            <Modal
                open={modalOpen}
                onClose={handleCloseModal}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <Box className={styles.modal}>
                    <h2>{editingTaskId ? 'Editar tarefa' : 'Criar nova tarefa'}</h2>
                    <TextField
                        label="Título"
                        fullWidth
                        value={newTask.title}
                        onChange={(e) => setNewTask({...newTask, title: e.target.value})}
                        margin="normal"
                    />
                    <TextField
                        label="Descrição"
                        fullWidth
                        value={newTask.description}
                        onChange={(e) => setNewTask({...newTask, description: e.target.value})}
                        margin="normal"
                    />
                    <Select
                        label="Prioridade"
                        fullWidth
                        value={newTask.priority}
                        onChange={(e) => setNewTask({...newTask, priority: e.target.value})}
                    >
                        <MenuItem value="NO_PRIORITY">Selecione a prioridade</MenuItem>
                        <MenuItem value="LOW">Baixa</MenuItem>
                        <MenuItem value="MEDIUM">Média</MenuItem>
                        <MenuItem value="HIGH">Alta</MenuItem>
                    </Select>
                    <Select
                        label="Tag"
                        fullWidth
                        value={newTask.tag}
                        onChange={(e) => setNewTask({...newTask, tag: e.target.value})}
                    >
                        <MenuItem value="SAUDE">Selecione a tag</MenuItem>
                        <MenuItem value="FITNESS">Fitness</MenuItem>
                        <MenuItem value="ELETRONICOS">Eletrônicos</MenuItem>
                        <MenuItem value="MERCADO">Mercado</MenuItem>
                        <MenuItem value="ESTUDO">Estudo</MenuItem>
                        <MenuItem value="LAZER">Lazer</MenuItem>
                        <MenuItem value="MOBILHA">Mobilha</MenuItem>
                        <MenuItem value="DESPESAS_CASA">Despesas Casa</MenuItem>
                    </Select>
                    <Select
                        label="Opções de data"
                        fullWidth
                        value={dateOption}
                        onChange={(e) => setDateOption(e.target.value)}
                    >
                        <MenuItem value="days_to_complete">Dias para completar</MenuItem>
                        <MenuItem value="due_date">Data de conclusão</MenuItem>
                    </Select>
                    {dateOption === 'days_to_complete' ? (
                        <TextField
                            label="Dias para completar"
                            fullWidth
                            type="number"
                            value={newTask.days_to_complete}
                            onChange={(e) => setNewTask({...newTask, days_to_complete: Number(e.target.value)})}
                            margin="normal"
                        />
                    ) : (
                        <TextField
                            label="Data de conclusão"
                            fullWidth
                            type="datetime-local"
                            value={newTask.due_date}
                            onChange={(e) => setNewTask({...newTask, due_date: e.target.value})}
                            margin="normal"
                        />
                    )}
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleCreateOrUpdateTask}
                        fullWidth
                        style={{marginTop: '16px'}}
                    >
                        {editingTaskId ? 'Update Task' : 'Create Task'}
                    </Button>
                    <Button
                        variant="outlined"
                        color="secondary"
                        onClick={handleCloseModal}
                        fullWidth
                        style={{marginTop: '8px'}}
                    >
                        Cancel
                    </Button>
                </Box>
            </Modal>
        </div>
    );
};

export default TaskIndex;


