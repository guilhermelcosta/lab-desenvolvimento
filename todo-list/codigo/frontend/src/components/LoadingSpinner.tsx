import React from 'react';
import {Triangle} from "react-loader-spinner";

const LoadingSpinner: React.FC = () => {
    return (
        // todo: passar estilo para arquivo css
        <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh'}}>
            <Triangle
                color={'blue'}
            />
        </div>
    );
};

export default LoadingSpinner;
