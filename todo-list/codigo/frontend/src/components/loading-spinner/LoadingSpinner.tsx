import React from 'react';
import {Triangle} from "react-loader-spinner";
import styles from './LoadingSpinner.module.css';

const LoadingSpinner: React.FC = () => {
    return (
        <div className={styles.spinnerContainer}>
            <Triangle
                color={'var(--primary-blue)'}
            />
        </div>
    );
};

export default LoadingSpinner;
