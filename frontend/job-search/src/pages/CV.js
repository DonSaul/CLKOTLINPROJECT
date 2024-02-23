import React from 'react';
import useSkills from '../hooks/useSkills';

const CV = () => {


  const { data: skills, isLoading, isError } = useSkills();

  if (isLoading) {
    return <p>Loading...</p>;
  }

  if (isError) {
    return <p>Error fetching skills</p>;
  }



  return (
    <div>
      <h2>CV Form Page</h2>

      <h1>Skills</h1>
      <ul>
        {skills.map((skill) => (
          <li key={skill.id}>{skill.name}</li>
        ))}
      </ul>

    </div>
  );
};

export default CV;