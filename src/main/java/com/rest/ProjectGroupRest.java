package com.rest;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aautushk on 9/13/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class ProjectGroupRest {
//    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

//    @Autowired
//    private IProjectGroupRepository projectGroupRepository;
//
//    @Autowired
//    private IGroupRepository groupRepository;
//
//    @RequestMapping(value = "/createProjectGroup", method = RequestMethod.POST)
//    @Transactional
//    public void createProjectGroup(@Valid @RequestBody ProjectGroupRequestDto projectGroupRequestDto) {
//        for(String group : projectGroupRequestDto.getGroups()){
//            GroupEntity groupEntity = new GroupEntity();
//            groupEntity.setGroupName(group);
//
//            groupRepository.save(groupEntity);//group saved
//
//            ProjectGroupEntity projectGroupEntity = new ProjectGroupEntity();
//            projectGroupEntity.setProjectGuid(projectGroupRequestDto.getProjectGuid());
//            projectGroupEntity.setGroupGuid(groupEntity.getGroupGuid());
//
//            projectGroupRepository.save(projectGroupEntity);//group is assigned to the project
//        }
//
//        return;
//    }
}
