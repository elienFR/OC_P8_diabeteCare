package com.mediscreen.microservicepatienthistory.model.utils.layout;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

  private PageItemType pageItemType;

  private int index;

  private boolean active;

}
