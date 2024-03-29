import React from "react";
import Autocomplete from "@mui/material/Autocomplete";
import TextField from "@mui/material/TextField";
import useJobFamily from "../hooks/useJobFamily";

const JobFamilyAutocomplete = ({
  value,
  onChange,
  label = "Select Job Family for Project",
}) => {
  const { data: jobFamilies } = useJobFamily();

  const getOptionValue = (option) => {
    // check if object is sent
    return typeof value === "object" ? option.id : option;
  };

  return (
    <Autocomplete
      options={jobFamilies || []}
      getOptionLabel={(option) => option.name || ""}
      value={
        jobFamilies?.find((option) => getOptionValue(option) === value) || null
      }
      isOptionEqualToValue={(option, val) => getOptionValue(option) === val}
      onChange={(e, newValue) => {
        onChange(newValue);
      }}
      renderInput={(params) => (
        <TextField {...params} label={label} margin="normal" />
      )}
    />
  );
};

export default JobFamilyAutocomplete;
