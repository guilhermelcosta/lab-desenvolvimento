import {Link} from "./Link";

export interface Task {
    id: string;
    title: string;
    description: string;
    priority: string;
    tag: string;
    due_date: Date;
    days_to_complete: number | null;
    completed_date: Date | null;
    is_completed: boolean;
    status: string;
    links: Link[];
}
