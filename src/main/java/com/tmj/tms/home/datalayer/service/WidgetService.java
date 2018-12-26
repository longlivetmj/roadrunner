package com.tmj.tms.home.datalayer.service;

import com.tmj.tms.home.datalayer.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetService extends JpaRepository<Widget, Integer> {

    List<Widget> findByRowNoAndStatusNotOrderByDisplayOrderAsc(Integer rowNo, Integer status);
}
