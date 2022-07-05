package com.mediscreen.clientui.model.utils.layout;

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
