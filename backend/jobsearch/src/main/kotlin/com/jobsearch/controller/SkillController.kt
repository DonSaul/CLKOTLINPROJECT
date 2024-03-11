package com.jobsearch.controller

import com.jobsearch.dto.SkillDTO
import com.jobsearch.service.SkillService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/skills")
class SkillController(val skillService: SkillService) {


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllSkills(): List<SkillDTO> {
        return skillService.retrieveAllSkills()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun createSkill(@Valid @RequestBody skillDTO: SkillDTO): SkillDTO {
        return skillService.createSkill(skillDTO)
    }

}