import { TextField } from "@mui/material";
import CardContainer from "../components/CardContainer"
import { useState } from "react";





export const CreateVacancy = () =>{


    const [companyName,setCompanyName]=useState();


    const handleSubmit= (data) =>{

        console.log("submit",data);
    }


    return (

        <>
        <CardContainer>
        Create vacancy

        <form onSubmit={handleSubmit}>


        <TextField
            label="Company name"
            type="text"
            value={null}
            onChange={(e) => setCompanyName(e.target.value)}
            fullWidth
            margin="normal"
            required
          />




        </form>



        </CardContainer>
       
        
        
        </>
    )
}