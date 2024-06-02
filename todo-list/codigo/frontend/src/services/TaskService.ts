import {Environment} from "../enviroments/Environment";
import {Task} from "../interfaces/Task";

export const getAllTasks = async (): Promise<Task[]> => {
    return await fetch(`${Environment.BASE_URL}/task`)
        .then(response => response.json());
};
