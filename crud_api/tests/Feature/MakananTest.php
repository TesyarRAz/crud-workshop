<?php

namespace Tests\Feature;

use App\Models\Makanan;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithFaker;
use Tests\TestCase;

class MakananTest extends TestCase
{
    public function test_index()
    {
        $response = $this->user()->json('GET', '/api/makanan');

        $response->assertStatus(200);
    }

    public function test_store()
    {
        $data = Makanan::factory()->make()->toArray();

        $response = $this->user()->json('POST', '/api/makanan', $data);

        $response->assertStatus(200);

        $this->assertDatabaseHas('makanans', $data);
    }

    public function test_show()
    {
        Makanan::factory()->create();

        $response = $this->user()->json('GET', '/api/makanan/1');

        $response->assertStatus(200);
    }

    public function test_update()
    {
        Makanan::factory()->create();

        $data = Makanan::factory()->make()->toArray();

        $response = $this->user()->json('PUT', '/api/makanan/1', $data);

        $response->assertStatus(200);

        $this->assertDatabaseHas('makanans', $data);
    }

    public function test_delete()
    {
        Makanan::factory()->create();
        
        $response = $this->user()->json('DELETE', '/api/makanan/1');

        $response->assertStatus(200);

        $this->assertDatabaseMissing('makanans', [
            'id' => 1,
        ]);
    }
}
