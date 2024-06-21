import styles from "../../pages/index/TaskIndex.module.css";

export class TextConstants {

    private static SEM_PRIORIDADE: string = 'Sem prioridade';
    private static PRIORIDADE_BAIXA: string = 'Baixa';
    private static PRIORIDADE_MEDIA: string = 'Média';
    private static PRIORIDADE_ALTA: string = 'Alta';

    public static ERROR_MESSAGES = {
        failedToFetchTasks: 'Failed to fetch tasks'
    };

    public static PRIORITY_NAMES :any = {
        NO_PRIORITY: {
            NAME: this.SEM_PRIORIDADE,
            STYLE: styles.noPriority
        },
        LOW: {
            NAME: this.PRIORIDADE_BAIXA,
            STYLE: styles.lowPriority
        },
        MEDIUM: {
            NAME: this.PRIORIDADE_MEDIA,
            STYLE: styles.mediumPriority
        },
        HIGH: {
            NAME: this.PRIORIDADE_ALTA,
            STYLE: styles.highPriority
        }
    };

}
