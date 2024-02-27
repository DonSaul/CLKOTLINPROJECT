import { useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';



const fetchVacancies = async (salary,jobFamilyId,yearsOfExperience) => {
  

  const queryParameters = new URLSearchParams({
    salary: salary || '',
    jobFamilyId: jobFamilyId || '',
    yearsOfExperience: yearsOfExperience || '',
  });

  const response = await fetch(`${ENDPOINTS.searchVacancies}?${queryParameters}`);
  console.log("vacancy response",response);

  if (!response.ok) {
    throw new Error('Failed to fetch vacancies');
  }
  return response.json();
};

const useGetVacancies = (salary, jobFamily, yearsOfExperience) => {
    return useQuery('vacancies', () => fetchVacancies(salary, jobFamily, yearsOfExperience), {
     // enabled: false,
    });
  };
export default useGetVacancies;