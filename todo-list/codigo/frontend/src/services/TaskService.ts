import {Environment} from "../enviroments/Environment";
import {Task} from "../interfaces/Task";

export const getAllTasks = async (): Promise<Task[]> => {
    return await fetch(`${Environment.BASE_URL}/task`, {
        method: "GET",
    }).then(response => response.json());
};

export const createTask = async (task: Task): Promise<Task> => {
    return await fetch(`${Environment.BASE_URL}/task`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task),
    }).then(response => response.json());
};

export const deleteTask = async (id: string): Promise<void> => {
    await fetch(`${Environment.BASE_URL}/task/${id}`, {
        method: "DELETE",
    });
};

export const updateTask = async (id: string, task: Task): Promise<Task> => {
    return await fetch(`${Environment.BASE_URL}/task/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(task),
    }).then(response => response.json());
};

export const updateIsCompletedTask = async (id: string): Promise<void> => {
    await fetch(`${Environment.BASE_URL}/task/${id}`, {
        method: "PATCH",
    });
};

