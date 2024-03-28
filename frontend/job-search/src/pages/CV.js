import React from 'react';
import useSkills from '../hooks/useSkills';
import { Autocomplete } from '@mui/material';
import { useState } from 'react';
import { TextField } from '@mui/material';
import Chip from '@mui/material/Chip';
import { useEffect } from 'react';
import Button from '@mui/material/Button';
import CardContainer from '../components/CardContainer';
import { Box } from '@mui/material';
import useJobFamily from '../hooks/useJobFamily';
import { useGetCurrentUserCv, useCV, useUpdateCV } from '../hooks/useCV';

import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import 'dayjs/locale/es';

const CV = () => {
  const [firstSave, setFirstSave] = useState(false);
  const [id, setId] = useState();
  const { data: cvData, error: cvError, isLoading: isCvLoading, isSuccess: isCvSuccess } = useGetCurrentUserCv();

  //Standard data cv
  const [yearsOfExperience, setYearsOfExperience] = useState('');
  const [salaryExpectation, setSalaryExpectation] = useState('');
  const [education, setEducation] = useState('');

  //Jobs
  const [jobs, setJobs] = useState([]);

  //Projects
  const [projects, setProjects] = useState([]);

  //Skills
  const { data: skills } = useSkills();
  const [selectedSkill, setSelectedSkill] = useState(null);
  const [selectedSkillsArray, setSelectedSkillsArray] = useState([]);
  const [availableSkills, setAvailableSkills] = useState([]);

  //Job family
  const { data: jobFamilies } = useJobFamily();

  //mutation
  const { mutate, isError, isSuccess } = useCV();
  const { mutate: updateCVbyID } = useUpdateCV();

  //simple way to fetch
  useEffect(() => {

    if (cvData) {
      setCvData(cvData);
    }

  }, [cvData])


  //skills
  useEffect(() => {
    if (skills && selectedSkillsArray.length > 0) {
      setAvailableSkills(skills.filter(skill => !selectedSkillsArray.some(selected => selected.skillId === skill.skillId)));
    } else if (skills) {
      setAvailableSkills(skills);
    }
  }, [skills, selectedSkillsArray]);

  const setCvData = (cvData) => {

    setId(cvData.id)
    setYearsOfExperience(cvData.yearsOfExperience);
    setSalaryExpectation(cvData.salaryExpectation);
    setEducation(cvData.education);
    setJobs(cvData.jobs.map(({ startDate, endDate, ...rest }) => {
      return { ...rest, startDate: dayjs(startDate), endDate: dayjs(endDate) }
    }));
    setProjects(cvData.projects);
    setSelectedSkillsArray(cvData.skills);

  }

  const handleRemoveSkill = (skillId) => {
    setSelectedSkillsArray((prevArray) => prevArray.filter((skill) => skill.skillId !== skillId));
  };

  const addJobField = () => {
    const newJob = {
      startDate: dayjs(),
      endDate: dayjs(),
      position: '',
      description: '',
      jobFamily: '',
    };

    setJobs([...jobs, newJob]);
  };

  const removeJobsField = (index) => {
    const updatedJobs = [...jobs];
    updatedJobs.splice(index, 1);
    setJobs(updatedJobs);
  };

  const addProjectField = () => {
    const newProject = {
      name: '',
      description: '',
      jobFamily: '',
    };

    setProjects([...projects, newProject]);
  };

  const removeProjectField = (index) => {
    const updatedProjects = [...projects];
    updatedProjects.splice(index, 1);
    setProjects(updatedProjects);
  };

  const handleJobChange = (index, field, value) => {
    const updatedJobs = [...jobs];
    updatedJobs[index][field] = value;
    setJobs(updatedJobs);
  };

  const handleProjectChange = (index, field, value) => {
    const updatedProjects = [...projects];
    updatedProjects[index][field] = value;
    setProjects(updatedProjects);
  };


  const handleSubmit = async (event) => {
    event.preventDefault();

    const formatedJobs = jobs.map(({ startDate, endDate, jobFamily, ...rest }) => {
      return {
        ...rest,
        startDate: startDate.format('YYYY-MM-DD'),
        endDate: endDate.format('YYYY-MM-DD'),
        jobFamilyId: jobFamily.id
      }
    })

    const formatedProjects = projects.map(({ jobFamily, ...rest }) => ({ ...rest, jobFamilyId: jobFamily.id }))
    const skillIds = selectedSkillsArray.map(skill => skill.skillId)

    const formData = {
      id,
      yearsOfExperience,
      salaryExpectation,
      education,
      skillIds,
      jobs: formatedJobs,
      projects: formatedProjects,
    };

    console.log(formData)

    try {
      if (id) {

        await updateCVbyID(formData);
      } else {

        await mutate(formData);

        setFirstSave(true);
      }
    } catch (error) {

      console.error('Error:', error);
    }

  };

  return (
    <div>

      <CardContainer>
        <Box display="flex" justifyContent="center">
          <Box flex="0 1 700px">
            <h1>Resume</h1>

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
                label="Salary Expectation"
                type="number"
                value={salaryExpectation}
                onChange={(e) => setSalaryExpectation(e.target.value)}
                fullWidth
                margin="normal"
                required
              />

              <h2>Jobs</h2>
              {jobs.map((job, index) => (
                <Box key={index} sx={{ border: '1px solid grey', padding: 2, marginBottom: 2 }}>
                  <Box display="flex" gap={2}>
                    <Box flex={1}>
                      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="es">
                        <DemoContainer components={['DatePicker']}>
                          <DatePicker
                            slotProps={{ textField: { fullWidth: true } }}
                            label={`Job ${index + 1} Start Date`}
                            value={job.startDate}
                            onChange={value => handleJobChange(index, 'startDate', value)}
                          />
                        </DemoContainer>
                      </LocalizationProvider>
                    </Box>
                    <Box flex={1}>
                      <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="es">
                        <DemoContainer components={['DatePicker']}>
                          <DatePicker
                            slotProps={{ textField: { fullWidth: true } }}
                            label={`Job ${index + 1} End Date`}
                            value={job.endDate}
                            onChange={value => handleJobChange(index, 'endDate', value)}
                          />
                        </DemoContainer>
                      </LocalizationProvider>
                    </Box>
                  </Box>
                  <TextField
                    label={`Job ${index + 1} Position`}
                    value={job.position}
                    onChange={(e) => handleJobChange(index, 'position', e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                  />
                  <TextField
                    label={`Job ${index + 1} Description`}
                    value={job.description}
                    onChange={(e) => handleJobChange(index, 'description', e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                  />
                  <Autocomplete
                    options={jobFamilies || []}
                    getOptionLabel={(option) => option.name || ''}
                    value={job.jobFamily || null}
                    isOptionEqualToValue={(option, value) => option.id === value.id}
                    onChange={(e, newValue) => handleJobChange(index, 'jobFamily', newValue)}
                    renderInput={(params) => <TextField {...params} label={`Select Job Family for Job ${index + 1}`} margin="normal" />}
                  />

                  <Button onClick={() => removeJobsField(index)} variant="outlined" color="secondary" sx={{ margin: '10px' }}>
                    {`Remove Job ${index + 1}`}
                  </Button>

                </Box>
              ))}

              <Button onClick={addJobField} variant="outlined" color="primary">
                {jobs.length ? 'Add Another Job' : 'Add A New Job'}
              </Button>

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
                    renderInput={(params) => <TextField {...params} label={`Select Job Family for Project ${index + 1}`} margin="normal" />}
                  />

                  <Button onClick={() => removeProjectField(index)} variant="outlined" color="secondary" sx={{ margin: '10px' }}>
                    {`Remove Project ${index + 1}`}
                  </Button>
                </Box>
              ))}

              <Button onClick={addProjectField} variant="outlined" color="primary">
                {projects.length ? 'Add Another Project' : 'Add A New Project'}
              </Button>

              <h2>Skills</h2>
              <Autocomplete
                id="skills-autocomplete"
                options={availableSkills}
                getOptionLabel={(option) => option.name}
                value={selectedSkill}
                isOptionEqualToValue={(option, value) => option.id === value.id}
                onChange={(event, newValue) => {
                  if (!newValue) return;
                  setSelectedSkill(newValue)
                  setSelectedSkillsArray((prevArray) => [...prevArray, newValue]);
                }
                }
                renderInput={(params) =>
                  <TextField {...params} label="Add skills" />
                }
                sx={{ marginBottom: 2 }}
              />

              {
                !!selectedSkillsArray.length &&
                <Box
                  my={4}
                  display="flex"
                  justifyContent="center"
                  flexWrap="wrap"
                  gap={.5}
                  p={2}
                  sx={{ borderRadius: '4px', border: '1px solid #1976d2' }}
                >
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
                </Box>
              }

              <Button type="submit" variant="contained" color="primary" >
                {id || firstSave ? 'Update CV' : 'Save CV'}
              </Button>

            </form>

          </Box>
        </Box>
      </CardContainer>

    </div>
  );
};

export default CV;