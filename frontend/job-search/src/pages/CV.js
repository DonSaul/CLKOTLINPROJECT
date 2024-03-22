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
import { useCV, useUpdateCV } from '../hooks/useCV';
import { Box } from '@mui/material';
import { Typography } from '@mui/material';
import useJobFamily from '../hooks/useJobFamily';
import { useGetCurrentUserCv } from '../hooks/useCV';

import IdTester from '../components/IdTester';
import JobFamilyAutocomplete from '../components/JobFamilyAutocomplete';

import SnackbarNotification from '../components/SnackbarNotification';
import { useAuth } from '../helpers/userContext';
const CV = () => {

  const [id, setId] = useState(); 
  const [hasFetchedData, setHasFetchedData] = useState(false);
  const { logout, getUserRole, isLoggedIn } = useAuth();
  const { data: cvData, error: cvError, isLoading: isCvLoading, isSuccess:isCvSuccess } = useGetCurrentUserCv();


  //Standard data cv
  const [yearsOfExperience, setYearsOfExperience] = useState('');
  const [salaryExpectations, setSalaryExpectations] = useState('');
  const [education, setEducation] = useState('');


  //tsting 
  const [projects, setProjects] = useState([{ name: '', description: '' }]);

  //Skills
  const { data: skills } = useSkills();
  const [selectedSkill, setSelectedSkill] = useState(null);
  const [selectedSkillsArray, setSelectedSkillsArray] = useState([]);
  const [availableSkills, setAvailableSkills] = useState([]);

  //Job family
  const { data: jobFamilies } = useJobFamily();


  //mutation
  const { mutate, isError, isSuccess } = useCV();
  const { mutate:updateCVbyID } = useUpdateCV();

//simple way to fetch
  useEffect( () => {

    if (cvData) {
      setCvData(cvData);
    }
    

  },[cvData])

 
//skills
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


  const setCvData = (cvData) =>{

      setId(cvData.id)
      setYearsOfExperience(cvData.yearsOfExperience);
      setSalaryExpectations(cvData.salaryExpectation);
      setEducation(cvData.education);
      setProjects(cvData.projects || [{ name: '', description: '' }]);
      setSelectedSkillsArray(cvData.skills || []);

  }

  const clearCv= () =>{
    setId('')
    setYearsOfExperience('');
    setSalaryExpectations('');
    setEducation('');
    setProjects([{ name: '', description: '' }]);
    setSelectedSkillsArray([]);

  }

  const handleRemoveSkill = (skillId) => {
    console.log(availableSkills);
    setSelectedSkillsArray((prevArray) => prevArray.filter((skill) => skill.skillId !== skillId));
  };

  const handleSendSkills = () => {

    console.log('Sending skills test:', selectedSkillsArray.map((skill) => skill.skillId));
  };


  const addProjectField = () => {
    setProjects([...projects, { name: '', description: '' }]);
  };

  const removeProjectField = (index) => {
    if (projects.length > 1) {
      const updatedProjects = [...projects];
      updatedProjects.splice(index, 1);
      setProjects(updatedProjects);
    }
  };

  const handleProjectChange = (index, field, value) => {
    const updatedProjects = [...projects];
    updatedProjects[index][field] = value;
    setProjects(updatedProjects);
  };


  const updateCV = () => {


    updateCVbyID()
    
  };








  const handleSubmit = async (event) => {
    event.preventDefault();

    //Quick fix for empty values
    if (
      !yearsOfExperience ||
      !salaryExpectations ||
      !education ||
      projects.some(project => !project.name || !project.description || !project.jobFamily) ||
      selectedSkillsArray.length === 0
    ) {
      console.log('Validation failed');
      return;
    }

    let longSkillString = selectedSkillsArray.map(skill => skill.skillId).join(",")

    const formattedProjects = projects.map(project => ({
      name: project.name,
      description: project.description,
      jobFamilyId: project?.jobFamily?.id,
      projectId:project?.projectId
    }));

    const formData = {
      yearsOfExperience,
      salaryExpectation: salaryExpectations,
      education,
      longSkillString,
      projects: formattedProjects,
      id
    };


    try {
      if (id) {
        
        await updateCVbyID(formData);
      } else {
       
        await mutate(formData);
      }
    } catch (error) {
     
      console.error('Error:', error);
    }
 



  };

  return (
    <div>


      <CardContainer>
        <h1>Curriculum</h1>

  
        <h2>General</h2>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Years of Experience"
            type="number"
            value={yearsOfExperience}
            onChange={(e) => setYearsOfExperience(e.target.value)}
            fullWidth
            margin="normal"
            required
          />
          <TextField
            label="Education"
            type="text"
            value={education}
            onChange={(e) => setEducation(e.target.value)}
            fullWidth
            margin="normal"
            required
          />

          <TextField
            label="Salary Expectations"
            type="number"
            value={salaryExpectations}
            onChange={(e) => setSalaryExpectations(e.target.value)}
            fullWidth
            margin="normal"
            required
          />


          <h2>Projects</h2>
          {projects.map((project, index) => (
            <Box key={index} sx={{ border: '1px solid grey', padding: 2, marginBottom: 2 }}>
              <TextField
                label={`Project ${index + 1} Name`}
                value={project.name}
                onChange={(e) => handleProjectChange(index, 'name', e.target.value)}
                fullWidth
                margin="normal"
                required
              />
              <TextField
                label={`Project ${index + 1} Description`}
                value={project.description}
                onChange={(e) => handleProjectChange(index, 'description', e.target.value)}
                fullWidth
                margin="normal"
                required
              />
               <Autocomplete
                options={jobFamilies || []}
                getOptionLabel={(option) => option.name || ''}
                value={project.jobFamily || null}
                isOptionEqualToValue={(option, value) => option.id === value.id}
                onChange={(e, newValue) => handleProjectChange(index, 'jobFamily', newValue)}
                renderInput={(params) => <TextField {...params} label={`Select Job Family for Project`} margin="normal"/>}
              />

              {/* 
              <JobFamilyAutocomplete 
              onChange={(e,newValue) => handleProjectChange(index, 'jobFamily', newValue)}
              label={`Select Job Family for Project`}
              value={project.jobFamily || null}
              />*/}

             


              {projects.length > 1 && (
                <Button onClick={() => removeProjectField(index)} variant="outlined" color="secondary">
                  Remove Project
                </Button>
              )}
            </Box>
          ))}

          <Button onClick={addProjectField} variant="outlined" color="primary">
            Add Another Project
          </Button>




          {/*  <TextField
            label="Projects"
            type="text"
            value={project}
            onChange={(e) => setProject(e.target.value)}
            fullWidth
            margin="normal"
          />
*/}

          <h2>Skills</h2>
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


          <Button type="submit" variant="contained" color="primary" >
        {id ? 'Update CV' : 'Save CV'}
      </Button>

        </form>




      </CardContainer>


    </div>
  );
};

export default CV;