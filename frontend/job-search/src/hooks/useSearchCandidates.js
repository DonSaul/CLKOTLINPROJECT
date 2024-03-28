import { useQuery } from 'react-query';
import { AUTH_TOKEN_NAME } from '../helpers/constants';
import { ENDPOINTS } from '../helpers/endpoints';

const fetchCandidates = async (salaryExpectation, jobFamilyId, yearsOfExperience) => {
    let token = localStorage.getItem(AUTH_TOKEN_NAME);
    const queryParameters = new URLSearchParams({
        salaryExpectation: salaryExpectation || '',
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

const useSearchCandidates = (salaryExpectation, jobFamilyId, yearsOfExperience) => {
    return useQuery('searchCandidates', () => fetchCandidates(salaryExpectation, jobFamilyId, yearsOfExperience));
};

export default useSearchCandidates;
