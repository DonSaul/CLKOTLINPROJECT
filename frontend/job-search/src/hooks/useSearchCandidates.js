import { useQuery } from 'react-query';
import { ENDPOINTS } from '../helpers/endpoints';

const fetchCandidates = async (filters) => {
    const response = await fetch(ENDPOINTS.searchCandidates, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(filters),
    });

    if (!response.ok) {
        throw new Error('Search failed');
    }

    const candidates = await response.json();
    return candidates;
};

export const useSearchCandidates = (filters) => {
    return useQuery(['searchCandidates', filters], () => fetchCandidates(filters));
};
