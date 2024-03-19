import { useState } from 'react';

export const useCheckboxField = (initialValue) => {
  const [value, setValue] = useState(initialValue);

  const handleCheckboxChange = (event) => {
    setValue(event.target.checked);
  };

  return [value, handleCheckboxChange];
};

