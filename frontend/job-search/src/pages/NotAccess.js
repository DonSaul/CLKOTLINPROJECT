import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';

const NotAccess = () => {


    return (
        <div>
            <CardContainer width='xs'>

               You don't have access to that resource.
            </CardContainer>

        </div>
    );
};

export default NotAccess;