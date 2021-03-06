/*
 * rs-rendernode is the worker node software of our RenderService.
 * Copyright (C) 2016 Wertarbyte <https://wertarbyte.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.lemaik.renderservice.regionprocessor.rendering;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Job {

  private String _id;
  private List<JobFile> files;
  private String texturepack;
  private boolean cancelled;

  public String getId() {
    return _id;
  }

  public String getSceneUrl() {
    return getUrl("scene").get();
  }

  public Optional<String> getFoliageUrl() {
    return getUrl("foliage");
  }

  public Optional<String> getGrassUrl() {
    return getUrl("grass");
  }

  public boolean hasOctree() {
    return files.stream().anyMatch(t -> t.getType().equalsIgnoreCase("octree"));
  }

  public String getOctreeUrl() {
    return getUrl("octree").get();
  }

  public Optional<String> getEmittergridUrl() {
    return getUrl("emittergrid");
  }

  public Optional<String> getSkymapUrl() {
    return files.stream().filter(t -> t.getType().equalsIgnoreCase("skymap")).findFirst()
        .map(JobFile::getUrl);
  }

  private Optional<String> getUrl(String type) {
    Optional<JobFile> file = files.stream().filter(t -> t.getType().equalsIgnoreCase(type))
        .findFirst();
    return file.map(JobFile::getUrl);
  }

  public String getTexturepack() {
    return texturepack;
  }

  public boolean isCancelled() {
    return cancelled;
  }

  public Stream<JobFile> getRegionUrls() {
    return files.stream().filter(t -> t.getType().equalsIgnoreCase("region"));
  }

  public class JobFile {

    private String type;
    private String name;
    private String url;

    public String getType() {
      return type;
    }

    public String getName() {
      return name;
    }

    public String getUrl() {
      return url;
    }
  }
}
