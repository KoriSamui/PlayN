/**
 * Copyright 2010 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.html;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;

import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.SurfaceLayer;
import playn.core.gl.Scale;

class HtmlGraphicsDom extends HtmlGraphics {

  private final HtmlGroupLayerDom rootLayer;

  public HtmlGraphicsDom(HtmlPlatform.Config config) {
    super(config);
    Element div = Document.get().createDivElement();
    div.getStyle().setOverflow(Overflow.HIDDEN);
    rootElement.appendChild(div);

    rootLayer = new HtmlGroupLayerDom(div);
  }

  @Override
  public void setSize(int width, int height) {
    super.setSize(width, height);
    rootLayer.element().getStyle().setWidth(width, Unit.PX);
    rootLayer.element().getStyle().setHeight(height, Unit.PX);
  }

  @Override
  public GroupLayer createGroupLayer() {
    return new HtmlGroupLayerDom();
  }

  @Override
  public GroupLayer.Clipped createGroupLayer(float width, float height) {
    throw new UnsupportedOperationException("Clipped group layer not supported by HTML/DOM");
  }

  @Override
  public ImageLayer createImageLayer() {
    return new HtmlImageLayerDom();
  }

  @Override
  public ImageLayer createImageLayer(Image img) {
    return new HtmlImageLayerDom(img);
  }

  @Override
  public SurfaceLayer createSurfaceLayer(float width, float height) {
    return new HtmlSurfaceLayerDom(width, height);
  }

  @Override
  public ImmediateLayer.Clipped createImmediateLayer(
      int width, int height, ImmediateLayer.Renderer renderer) {
    throw new UnsupportedOperationException("Immediate layer not supported by HTML/DOM");
  }

  @Override
  public ImmediateLayer createImmediateLayer(ImmediateLayer.Renderer renderer) {
    throw new UnsupportedOperationException("Immediate layer not supported by HTML/DOM");
  }

  @Override
  public int height() {
    return rootLayer.element().getOffsetHeight();
  }

  @Override
  public HtmlGroupLayerDom rootLayer() {
    return rootLayer;
  }

  @Override
  public int width() {
    return rootLayer.element().getOffsetWidth();
  }

  @Override
  Scale scale() {
    return Scale.ONE;
  }

  @Override
  void paint(Game game, float paintAlpha) {
    game.paint(paintAlpha);
    rootLayer.update();
  }

  @Override
  Element rootElement() {
    return rootLayer.element();
  }
}
