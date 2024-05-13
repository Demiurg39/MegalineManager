package org.megaline.database.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

interface InterfaceManager<M> {
  void create(M model) throws SQLException;

  void update(M model, String ... args) throws SQLException;

  void delete(M model) throws SQLException;

  ResultSet get(M model) throws SQLException;
}
