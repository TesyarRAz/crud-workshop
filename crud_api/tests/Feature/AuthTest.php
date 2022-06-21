<?php

namespace Tests\Feature;

use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Foundation\Testing\WithFaker;
use Tests\TestCase;

class AuthTest extends TestCase
{
    public function test_login()
    {
        $response = $this->json('POST', '/api/login', [
            'username' => 'admin',
            'password' => 'password',
        ]);

        $response->assertStatus(200)->assertJsonStructure([
            'token',
            'message',
        ]);
    }
}
