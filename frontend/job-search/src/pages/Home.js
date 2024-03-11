import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import { useAuth } from '../helpers/userContext';
const Home = () => {
    const {getUserEmail,isLoggedIn} = useAuth();


    return (
        <div>
            <CardContainer width='xs'>

               

                { isLoggedIn? (<>

                    Hello {getUserEmail()}




                </>)
                : 
                (<>
                
                
                
                Hello! Remember you need to login!
                
                
                
                </>)

                }

            </CardContainer>

        </div>
    );
};

export default Home;