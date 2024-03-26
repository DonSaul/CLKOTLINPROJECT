import React, { useState } from 'react';
import CardContainer from '../components/CardContainer';
import CurrentUserInfo from '../components/CurrentUserInfo';
import UserAvatar from '../components/UserAvatar';

const Profile = () => {


    return (
        <div>
            <h3>Profile</h3>
            <CardContainer width='xs'>
                <UserAvatar></UserAvatar>
                <CurrentUserInfo></CurrentUserInfo>
                
            </CardContainer>

        </div>
    );
};

export default Profile;