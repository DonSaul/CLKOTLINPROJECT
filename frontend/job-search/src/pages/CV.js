import React from 'react';
import useSkills from '../hooks/useSkills';
import { Autocomplete } from '@mui/material';
import { useState } from 'react';
import { TextField } from '@mui/material';
//import { Button } from '@mui/base';
import Chip from '@mui/material/Chip';
import { useEffect } from 'react';
import Button from '@mui/material/Button';
import SimpleContainer from '../components/SimpleContainer';
import CardContainer from '../components/CardContainer';
import { useCV } from '../hooks/useCV';
import { Box } from '@mui/material';
const CV = () => {
  //Standard data cv
  const [yearsOfExperience, setYearsOfExperience] = useState('');
  const [salaryExpectations, setSalaryExpectations] = useState('');
  const [education, setEducation] = useState('');
  const [projects, setProjects] = useState('');

  //Skills
  const { data: skills, isLoading, isError } = useSkills();
  const [selectedSkill, setSelectedSkill] = useState(null);
  const [selectedSkillsArray, setSelectedSkillsArray] = useState([]);
  const [availableSkills, setAvailableSkills] = useState([]);


  //mutation
  const { mutate } = useCV();



  useEffect(() => {
    if (skills && selectedSkillsArray.length > 0) {
      console.log("entering condition", skills.filter(skill => !selectedSkillsArray.some(selected => selected.skillId === skill.skillId)))
      setAvailableSkills(skills.filter(skill => !selectedSkillsArray.some(selected => selected.skillId === skill.skillId)));
    } else if (skills) {
      console.log("no selected")
      setAvailableSkills(skills);
    }
  }, [skills, selectedSkillsArray]);



  //Testing

  const handleRemoveSkill = (skillId) => {
    console.log(availableSkills);
    setSelectedSkillsArray((prevArray) => prevArray.filter((skill) => skill.skillId !== skillId));
  };

  const handleSendSkills = () => {

    console.log('Sending skills test:', selectedSkillsArray.map((skill) => skill.skillId));
  };


  if (isLoading) {
    return <p>Loading...</p>;
  }

  if (isError) {
    return <p>Error fetching skills</p>;
  }




  const handleSubmit = (event) => {
    event.preventDefault();

    console.log('Form submitted:', { yearsOfExperience, salaryExpectations, education, projects });
    console.log("skills", selectedSkillsArray);

    let longSkillString = selectedSkillsArray.map(skill => skill.skillId).join(",")
    console.log("Skill long string: ", longSkillString);

    const formData = {
      yearsOfExperience,
      salaryExpectations,
      education,
      projects,
      longSkillString
    };

    mutate(formData)




  };

  return (
    <div>


      <CardContainer>
        <h2>Curriculum</h2>


        <form onSubmit={handleSubmit}>
          <TextField
            label="Years of Experience"
            type="number"
            value={yearsOfExperience}
            onChange={(e) => setYearsOfExperience(e.target.value)}
            fullWidth
            margin="normal"
          />
           <TextField
            label="Education"
            type="text"
            value={education}
            onChange={(e) => setEducation(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Projects"
            type="text"
            value={projects}
            onChange={(e) => setProjects(e.target.value)}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Salary Expectations"
            type="text"
            value={salaryExpectations}
            onChange={(e) => setSalaryExpectations(e.target.value)}
            fullWidth
            margin="normal"
          />
         


          <Autocomplete
            id="skills-autocomplete"
            options={availableSkills}
            getOptionLabel={(option) => option.name}
            value={selectedSkill}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            onChange={(event, newValue) => {
              if (!newValue) {
                return;
              }

              setSelectedSkill(newValue)
              setSelectedSkillsArray((prevArray) => [...prevArray, newValue]);
            }
            }
            renderInput={(params) =>
              <TextField {...params} label="Add skills" />
            }
          />



          <Box
            mx="auto"
            height="auto"
            width="80%" 
            my={4}
            display="flex"
            flexDirection="column"
            alignItems="center"
            gap={4}
            p={2}
            sx={{ border: '2px solid grey' }}
          >
            <div>
              {selectedSkillsArray.map((skill) => (
                <Chip
                  key={skill.skillId}
                  label={skill.name}
                  onDelete={() => {
                    handleRemoveSkill(skill.skillId);
                  }}
                  color="primary"
                />
              ))}
            </div>
          </Box>


          <Button type="submit" variant="contained" color="primary">
            Submit
          </Button>

        </form>


  

      </CardContainer>


    </div>
  );
};

export default CV;