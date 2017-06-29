package com.serverless.persistence.repository

import java.util.List

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import com.serverless.persistence.entity.User

import groovy.transform.CompileStatic

@CompileStatic
@Repository
interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByLastName(String lastName)
}