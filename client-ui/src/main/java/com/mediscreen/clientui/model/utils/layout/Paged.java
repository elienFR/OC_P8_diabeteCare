package com.mediscreen.clientui.model.utils.layout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paged<T> {
  private RestResponsePage<T> page;

  private Paging paging;
}
