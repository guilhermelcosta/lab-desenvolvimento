import React, {useEffect, useState} from 'react';
import {createTask, deleteTask, getAllTasks, updateIsCompletedTask, updateTask} from '../../services/TaskService';
import {Task} from "../../interfaces/Task";
import LoadingSpinner from "../../components/loading-spinner/LoadingSpinner";
import Snackbar from '@mui/material/Snackbar';
import styles from './TaskIndex.module.css';
import {Box, Button, Checkbox, IconButton, MenuItem, Modal, Select, TextField} from "@mui/material";
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
        days_to_complete: '',
        due_date: '',
        status: '',
    });
    const [editingTaskId, setEditingTaskId] = useState<string | null>(null);
    const [dateOption, setDateOption] = useState<string>('days_to_complete');
    const tasksPerPage: number = NumberConstants.FIVE;
    const offset: number = currentPage * tasksPerPage;
    const filteredTasks = tasks.filter(task => !task.is_completed); // Filtra as tarefas que não estão completas
    const currentTasks: Task[] = filteredTasks.slice(offset, offset + tasksPerPage);
    const pageCount: number = Math.ceil(filteredTasks.length / tasksPerPage);

    useEffect((): void => {
        fetchTasks();
    }, []);

    const fetchTasks = async (): Promise<void> => {
        try {
            const tasksFound: Task[] = await getAllTasks();
            tasksFound.sort((a, b) => a.title.localeCompare(b.title));
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
                days_to_complete: '',
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

    const handleTaskCompletion = async (task: Task) => {
        try {
            task.is_completed = true;
            setSnackbarMessage('Tarefa concluída com sucesso');
            await updateIsCompletedTask(task.id);
            await fetchTasks();
        } catch (error) {
            setSnackbarMessage('Falha ao atualizar status da tarefa');
            setOpen(true);
        }
    };

    function handleDueDate(value: string) {
        var dataObjeto = new Date(value);
        var dia = dataObjeto.getDate();
        var mes = dataObjeto.getMonth() + 1; // Lembrando que o mês em JavaScript é baseado em zero (janeiro é 0)
        var ano = dataObjeto.getFullYear();
        var horas = dataObjeto.getHours();
        var minutos = dataObjeto.getMinutes();
        var segundos = dataObjeto.getSeconds();
        var dataFormatada = dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
        newTask.due_date = dataFormatada;

    }

    return (
        <div className={styles.taskContainer}>
            <ul>
                <IconButton
                    onClick={() => handleOpenModal()}>
                    <FontAwesomeIcon icon={faPlus} className={styles.addButton}/>
                </IconButton>

                {currentTasks.map(task => (
                    <li key={task.id}
                        className={`${styles.taskCard} ${task.is_completed ? 'completed' : ''}`}>
                        <Checkbox
                            className={styles.customCheckbox}
                            checked={task.is_completed}
                            onChange={() => handleTaskCompletion(task)}
                        />
                        <div className={styles.taskTitleAndDescription} onClick={() => handleOpenModal(task)}>
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
                                <FontAwesomeIcon icon={faTrash} style={{color: '#90DE1B'}}/>
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
                        className={styles.modalItem}
                        onChange={(e) => setNewTask({...newTask, title: e.target.value})}
                        margin="normal"
                    />
                    <TextField
                        label="Descrição"
                        fullWidth
                        value={newTask.description}
                        className={styles.modalItem}
                        onChange={(e) => setNewTask({...newTask, description: e.target.value})}
                        margin="normal"
                    />
                    <Select
                        label="Prioridade"
                        fullWidth
                        value={newTask.priority}
                        className={styles.modalItem}
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
                        className={styles.modalItem}
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
                        className={styles.modalItem}
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
                            className={styles.modalItem}
                            onChange={(e) => setNewTask({...newTask, due_date: handleDueDate(e.target.value)})}
                            margin="normal"
                        />
                    )}
                    <Button
                        variant="contained"
                        onClick={handleCreateOrUpdateTask}
                        fullWidth
                        className={styles.createEditButton}
                    >
                        {editingTaskId ? 'Editar' : 'Criar'}
                    </Button>
                    <Button
                        variant="outlined"
                        onClick={handleCloseModal}
                        fullWidth
                        className={styles.cancelButton}
                    >
                        Cancel
                    </Button>
                </Box>
            </Modal>
        </div>
    );
};

export default TaskIndex;


