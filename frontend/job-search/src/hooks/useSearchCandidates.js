import { useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';
import { AUTH_TOKEN_NAME } from '../helpers/constants';

const fetchCandidates = async (salary, jobFamilyId, yearsOfExperience) => {
    let token = localStorage.getItem(AUTH_TOKEN_NAME);
    const queryParameters = new URLSearchParams({
        salary: salary || '',
        jobFamilyId: jobFamilyId || '',
        yearsOfExperience: yearsOfExperience || '',
    });
    const response = await fetch(`${ENDPOINTS.searchCandidates}?${queryParameters}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error('Search failed');
    }
    const candidates = await response.json();
    return candidates.data;
};

const useSearchCandidates = (salary, jobFamily, yearsOfExperience) => {
    return useQuery('searchCandidates', () => fetchCandidates(salary, jobFamily, yearsOfExperience));
};

export default useSearchCandidates;
