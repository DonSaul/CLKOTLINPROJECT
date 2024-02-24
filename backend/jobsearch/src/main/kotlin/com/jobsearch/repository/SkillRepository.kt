package com.jobsearch.repository

import com.jobsearch.entity.Skill
import org.springframework.data.jpa.repository.JpaRepository


interface SkillRepository :JpaRepository<Skill,Int> {


}