// Register.js
import React, { useState } from 'react';
import RegisterForm from '../components/RegisterForm';
import CardContainer from '../components/CardContainer';

const Register = () => {
    return (
        <div>
            <CardContainer width='xs'>

                <RegisterForm ></RegisterForm>
            </CardContainer>

        </div>
    );
};

export default Register;