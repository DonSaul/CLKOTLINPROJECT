import React, { useState } from 'react';
import { useSearchCandidates } from '../hooks/useSearchCandidates';

const ManagerSearch = () => {
    const [filters, setFilters] = useState({
        yearsOfExperience: null,
        jobFamily: null,
        salaryExpectation: null
    });

    const { data: candidates, isLoading, error } = useSearchCandidates(filters);

    // TODO: Add form for filters and list for candidates

    return <div>Manager Search</div>;
};

export default ManagerSearch;
