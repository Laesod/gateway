package com.rest;

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
